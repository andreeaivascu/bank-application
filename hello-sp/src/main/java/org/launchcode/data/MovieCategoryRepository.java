package org.launchcode.data;

import org.launchcode.models.MovieCategory;
import org.springframework.data.repository.CrudRepository;

public interface MovieCategoryRepository extends CrudRepository<MovieCategory, Integer> {
}
