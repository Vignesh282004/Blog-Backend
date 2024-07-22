package com.blogapi.Daos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDao {
	private int categoryId;
	
	@NotBlank(message = "category can't be blank ")
	@Size(min = 4,message = "Category type must be greater than 4 character")
	private String type;
	
	@NotBlank(message = "discription of category can't be blank")
	@Size(min = 7, message = "Description of category must be greater than or equal to 10 character")
	private String discription;
}
