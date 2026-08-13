package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.mapper.CategoryMapper;
import com.devsuperior.dscommerce.mapper.ProductMapper;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryMapper categoryMapper;

    public ProductService(ProductRepository repository, ProductMapper mapper, CategoryMapper categoryMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return mapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
        return repository
                .searchByName(name, pageable)
                .map(mapper::toMinDTO);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        product = repository.save(product);
        return mapper.toDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = repository.getReferenceById(id);
        Set<Category> categoriesSet = dto.categories().stream()
                    .map(categoryMapper::toEntity)
                    .collect(Collectors.toSet());
        product.updateData(dto.name(), dto.description(), dto.price(), dto.imgUrl(), categoriesSet);
        product = repository.save(product);
        return mapper.toDTO(product);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ResourceNotFoundException();

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
