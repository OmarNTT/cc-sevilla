package com.bookstore.service.implemented;


import com.bookstore.Repository.IBookRegistryRepository;
import com.bookstore.Response.BookRegistryResponse;
import com.bookstore.model.BookRegistry;
import com.bookstore.service.IBookRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRegistryService implements IBookRegistryService {

    @Autowired
    private IBookRegistryRepository iBookRegistryRepository;

    @Override
    @Cacheable(value="registries")
    public List<BookRegistryResponse> getAllBookRegistry(){
        return iBookRegistryRepository.findAll().stream()
                .map(BookRegistryResponse::new)
                .collect(Collectors.toList());
    }
    @Override
    @Cacheable(value="registries",key="#id")
    public List<BookRegistryResponse> getBookRegistryById(String id) {
        return iBookRegistryRepository.findById(id).stream()
                .map(BookRegistryResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value="registries",allEntries=true)
    public BookRegistryResponse postBookRegistry(BookRegistry bookRegistry) {
        BookRegistry newBookRegistry = new BookRegistry(bookRegistry.getMessage(),bookRegistry.getDate());
        return new BookRegistryResponse(iBookRegistryRepository.save(newBookRegistry));
    }

    @Override
    @CacheEvict(value="registries",allEntries=true)
    public BookRegistryResponse updateBookRegistry(String id, BookRegistry bookRegistry) {
        if(bookRegistry == null) {
            return null;
        }
        var opt = iBookRegistryRepository.findById(id);
        if(opt.isEmpty())
            return null;
        BookRegistry prevBookRegistry = opt.get();
        prevBookRegistry.setMessage(bookRegistry.getMessage());
        prevBookRegistry.setDate(bookRegistry.getDate());
        return new BookRegistryResponse(iBookRegistryRepository.save(prevBookRegistry));
    }

    @Override
    @CacheEvict(value="registries",allEntries=true)
    public void deleteBookRegistryById(String id) {
        iBookRegistryRepository.deleteById(id);
    }


}
