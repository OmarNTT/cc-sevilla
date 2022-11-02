package com.bookstore.service.implemented;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bookstore.Response.EditorialResponse;
import com.bookstore.service.IEditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bookstore.Repository.IEditorialRepository;
import com.bookstore.model.Editorial;

@Service
public class EditorialService implements IEditorialService {

    @Autowired
    public IEditorialRepository editorialRepo;

    @Override
    @Cacheable(value="editorials")
    public List<EditorialResponse> getAllEditorials(){
        return this.editorialRepo.findAll().stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value="editorials",key="#id")
    public List<EditorialResponse> getEditorialById(long id) {
        return this.editorialRepo.findById(id).stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value="editorials")
    public List<EditorialResponse> getEditorialByName(String name) {
        return this.editorialRepo.findEditorialByName(name).stream()
                .map((editorial)->new EditorialResponse(editorial))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "editorials", allEntries=true)
    public EditorialResponse postEditorial(Editorial editorial) {
        if(editorial == null || editorial.getName() == null || editorial.getName().length() == 0) {
            return null;
        }
        return new EditorialResponse(this.editorialRepo.save(editorial));
    }

    @Override
    @CacheEvict(value = "editorials", allEntries=true)
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

    @Override
    @CacheEvict(value = "editorials", allEntries=true)
    public void deleteEditorialById(long id) {
        this.editorialRepo.deleteById(id);
    }



}
