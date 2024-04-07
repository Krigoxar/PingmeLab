package com.pingme.ping.services;

import com.pingme.ping.daos.CategoryRepository;
import com.pingme.ping.daos.UrlRepository;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.daos.model.ObservedUrl;
import com.pingme.ping.dtos.NewURL;
import com.pingme.ping.dtos.ObservationsCount;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ObservedUrlService {

  private UrlRepository observedUrlRepo;
  private CategoryRepository categoryRepo;

  public ObservedUrlService(UrlRepository observedUrlRepo, CategoryRepository categoryRepo) {
    this.observedUrlRepo = observedUrlRepo;
    this.categoryRepo = categoryRepo;
  }

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

  public ObservedUrl addObservedUrl(NewURL url) {
    return observedUrlRepo.save(new ObservedUrl(url.url(), new Date()));
  }

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

  public ObservationsCount getObservatioinsCount(String url) {
    var target = observedUrlRepo.findByUrl(url);
    if (target.isEmpty()) {
      return null;
    }
    Long count = observedUrlRepo.countObservations(target.get(0));
    return new ObservationsCount(count, url);
  }
}
