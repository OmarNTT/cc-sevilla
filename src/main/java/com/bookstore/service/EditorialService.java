package com.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bookstore.model.Editorial;
import com.bookstore.repository.IEditorialRepository;
import com.bookstore.response.EditorialResponse;

@Service
public class EditorialService {

    @Autowired
    public IEditorialRepository editorialRepo;
    
    @Cacheable("editorials")
    public List<EditorialResponse> getAllEditorials(){
        return this.editorialRepo.findAll().stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }
    
    
    @Cacheable(value = "editorials", key="#id")
    public Optional<Editorial> getEditorialById(long id) {
        return this.editorialRepo.findById(id);
    }
    
    @Cacheable("editorials")
    public List<EditorialResponse> getEditorialByName(String name) {
        return this.editorialRepo.findEditorialByName(name).stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }
    
    @CacheEvict(value = "editorials", allEntries = true)
    public EditorialResponse postEditorial(Editorial editorial) {
        if(editorial == null || editorial.getName() == null || editorial.getName().length() == 0) {
            return null;
        }

        return new EditorialResponse(this.editorialRepo.save(editorial));
    }
    
    @CacheEvict(value = "editorials", allEntries = true)
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
    
    @CacheEvict(value = "editorials", allEntries = true)
    public void deleteEditorialById(long id) {
        this.editorialRepo.deleteById(id);
    }



}
