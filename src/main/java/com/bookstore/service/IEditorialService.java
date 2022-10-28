package com.bookstore.service;

import com.bookstore.Response.EditorialResponse;
import com.bookstore.model.Editorial;

import java.util.List;

public interface IEditorialService {

    public List<EditorialResponse> getAllEditorials();
    public List<EditorialResponse> getEditorialById(long id);
    public List<EditorialResponse> getEditorialByName(String name);
    public EditorialResponse postEditorial(Editorial editorial);
    public EditorialResponse updateEditorial(long id, Editorial editorial);
    public void deleteEditorialById(long id);

}
