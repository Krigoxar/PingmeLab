package com.pingme.ping.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.pingme.ping.dtos.*;
import com.pingme.ping.daos.model.Category;
import com.pingme.ping.services.CategoryService;
import com.pingme.ping.services.ObservedURLService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CategoryController {
    
    private CategoryService categoryService;
    private ObservedURLService observedService;

    CategoryController(CategoryService categoryService, ObservedURLService observedService) {
        this.categoryService = categoryService;
        this.observedService = observedService;
    }

    @GetMapping("/categorys")
	public ResponseEntity<List<Category>> getAllTutorials(@RequestParam(required = false) String url) {
		List<Category> categorys;
	
		if (url == null){
			categorys = categoryService.getAllCategorys();
		}
		else{
			categorys = categoryService.getCategoryByName(url);
		}
	
		if (categorys.isEmpty()) {
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	
		return new ResponseEntity<>(categorys, HttpStatus.OK);
  	}
	
	@PostMapping("/categorys")
	public Category createNewCategory(@RequestBody(required = true) CategoryName name) {
		return categoryService.addCategory(name);
	}

	@DeleteMapping("/categorys/{id}")
	public HttpStatus deleteCategory(@PathVariable Long id) {
		if(categoryService.deleteCategory(id)) {
			return HttpStatus.NO_CONTENT;
		}
		return HttpStatus.NOT_FOUND;
	}
    
    @PutMapping("categorys/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category entity) {
		var res = categoryService.updateCategory(entity, id);
		if(res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("categorys/{id}/url")
	public ResponseEntity<Category> addURLCategory(@PathVariable Long id, @RequestParam Long uRLid) {

		var res = observedService.putToCategory(uRLid, id);
		if(res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

    @DeleteMapping("categorys/{id}/url")
	public ResponseEntity<Category> removeURLCategory(@PathVariable Long id, @RequestParam Long uRLid) {

		var res = observedService.removeFromCategory(uRLid, id);
		if(res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
