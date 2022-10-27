package com.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bookstore.Response.EditorialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.Repository.IEditorialRepository;
import com.bookstore.model.Editorial;

@Service
public class EditorialService {

    @Autowired
    public IEditorialRepository editorialRepo;

    public List<EditorialResponse> getAllEditorials(){
        return this.editorialRepo.findAll().stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    public List<EditorialResponse> getEditorialById(long id) {
        return this.editorialRepo.findById(id).stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    public List<EditorialResponse> getEditorialByName(String name) {
        return this.editorialRepo.findEditorialByName(name).stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    public EditorialResponse postEditorial(Editorial editorial) {
        if(editorial == null || editorial.getName() == null || editorial.getName().length() == 0) {
            return null;
        }

        return new EditorialResponse(this.editorialRepo.save(editorial));
    }

    public EditorialResponse updateEditorial(long id, Editorial editorial) {
        if(editorial == null) {
            return null;
        }
        Optional<Editorial> opt = this.editorialRepo.findById(id);
        if(opt.isEmpty())
            return null;

        Editorial prevEditorial = opt.get();
        prevEditorial.setName(editorial.getName());

        return new EditorialResponse(this.editorialRepo.save(prevEditorial));
    }

    public void deleteEditorialById(long id) {
        this.editorialRepo.deleteById(id);
    }



}
