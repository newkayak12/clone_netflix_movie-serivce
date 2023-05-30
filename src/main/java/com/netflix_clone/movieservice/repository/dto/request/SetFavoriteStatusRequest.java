package com.netflix_clone.movieservice.repository.dto.request;

import com.netflix_clone.movieservice.repository.dto.reference.FavoriteDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SetFavoriteStatusRequest extends FavoriteDto{
}
