package com.pingme.ping.services;

import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewUrl;
import com.pingme.ping.dtos.ObservationsCount;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The `ObservedUrlService` class in Java provides methods for managing observed URLs and
 * categories.
 */
@Service
public class ObservedUrlService {

  private UrlRepository observedUrlRepo;
  private CategoryRepository categoryRepo;

  public ObservedUrlService(UrlRepository observedUrlRepo, CategoryRepository categoryRepo) {
    this.observedUrlRepo = observedUrlRepo;
    this.categoryRepo = categoryRepo;
  }

  /**
   * This Java function adds a URL to a category by retrieving the category and URL objects from
   * repositories and updating the category's list of URLs.
   *
   * @param urlId The `urlId` parameter is the unique identifier of the URL that you want to
   *     associate with a category.
   * @param categoryId The `categoryId` parameter represents the unique identifier of the category
   *     to which you want to add the URL.
   * @return The method `putToCategory` is returning an instance of the `Category` class.
   */
  @Transactional
  public Category putToCategory(Long urlId, Long categoryId) {
    var bag = categoryRepo.findById(categoryId);
    var url = observedUrlRepo.findById(urlId);
    if (bag.isEmpty() || url.isEmpty()) {
      return null;
    }

    var category = bag.get();
    category.getUrls().add(url.get());

    return categoryRepo.save(category);
  }

  /**
   * The function removes a URL from a category and saves the updated category.
   *
   * @param urlId The `urlId` parameter is the unique identifier of the URL that you want to remove
   *     from a specific category.
   * @param categoryId The `categoryId` parameter represents the unique identifier of the category
   *     from which you want to remove a URL.
   * @return The `removeFromCategory` method returns a `Category` object after removing a specific
   *     URL from the category's list of URLs and saving the updated category in the repository. If
   *     the category or URL is not found, or if the URL is not present in the category's list, the
   *     method returns `null`.
   */
  @Transactional
  public Category removeFromCategory(Long urlId, Long categoryId) {
    var bag = categoryRepo.findById(categoryId);
    var url = observedUrlRepo.findById(urlId);
    if (bag.isEmpty() || url.isEmpty()) {
      return null;
    }

    var category = bag.get();
    if (!category.getUrls().contains(url.get())) {
      return null;
    }

    category.getUrls().remove(url.get());

    return categoryRepo.save(category);
  }

  public List<ObservedUrl> getAllObservableUrls() {
    return observedUrlRepo.findAll();
  }

  public List<ObservedUrl> getObservableUrlbyUrl(String url) {
    return observedUrlRepo.findByUrl(url);
  }

  /** This Java function. */
  public ObservedUrl addObservedUrl(NewUrl url) {
    if (url == null) {
      return null;
    }
    return observedUrlRepo.save(new ObservedUrl(url.url(), new Date()));
  }

  /**
   * This Java function deletes an observed URL by removing it from its associated categories and
   * then deleting it from the repository.
   *
   * @param id The `id` parameter is of type `Long` and represents the unique identifier of the
   *     observed URL that needs to be deleted from the repository.
   * @return The method `deleteObservedUrl` returns a boolean value - `true` if the observed URL
   *     with the specified ID was successfully deleted, and `false` if the observed URL with the
   *     specified ID was not found in the repository.
   */
  @Transactional
  public boolean deleteObservedUrl(Long id) {
    var forDelete = observedUrlRepo.findById(id);

    if (forDelete.isEmpty()) {
      return false;
    }

    List<Category> categories = new LinkedList<>();

    forDelete.get().getBags().forEach(categories::add);

    for (Category category : categories) {
      category.getUrls().remove(forDelete.get());
    }

    observedUrlRepo.delete(forDelete.get());
    return true;
  }

  /**
   * The function updates an ObservedUrl entity with new date and URL based on the provided entity
   * and ID.
   *
   * @param entity The `entity` parameter in the `updateObservedUrl` method represents the new data
   *     that you want to update for an existing `ObservedUrl` entity. It typically contains the
   *     updated `date` and `url` values that you want to set for the `ObservedUrl` entity with
   * @param id The `id` parameter is a `Long` value that represents the unique identifier of the
   *     ObservedUrl entity that needs to be updated.
   * @return The method `updateObservedUrl` is returning an `ObservedUrl` object after updating its
   *     date and URL properties based on the provided `entity` parameter. If the `id` provided does
   *     not correspond to an existing `ObservedUrl` in the repository, the method returns `null`.
   */
  public ObservedUrl updateObservedUrl(ObservedUrl entity, Long id) {

    var observedUrlDb = observedUrlRepo.findById(id);
    if (!observedUrlDb.isPresent()) {
      return null;
    }

    var observedUrl = observedUrlDb.get();
    observedUrl.setDate(entity.getDate());
    observedUrl.setUrl(entity.getUrl());
    return observedUrl;
  }

  /**
   * The function retrieves the count of observations for a given URL from a repository and returns
   * an ObservationsCount object.
   *
   * @param url The `url` parameter is a String that represents the URL for which you want to
   *     retrieve the observations count.
   * @return An instance of ObservationsCount is being returned, which contains the count of
   *     observations for the given URL. If the URL is not found in the repository, null is
   *     returned.
   */
  public ObservationsCount getObservatioinsCount(String url) {
    var target = observedUrlRepo.findByUrl(url);
    if (target.isEmpty()) {
      return null;
    }
    Long count = observedUrlRepo.countObservations(target.get(0));
    return new ObservationsCount(count, url);
  }

  /**
   * the addObservedUrls.
   *
   * @param urlDto the urlDto
   * @return the call
   */
  public List<ObservedUrl> addObservedUrls(List<NewUrl> urlDto) {
    if (urlDto == null) {
      return new LinkedList<>();
    }
    if (urlDto.isEmpty()) {
      return new LinkedList<>();
    }

    var res = new LinkedList<ObservedUrl>();

    urlDto.stream()
        .distinct()
        .forEach(url -> res.add(observedUrlRepo.save(new ObservedUrl(url.url()))));

    return res;
  }
}
