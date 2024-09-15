package Main.service;

import Main.DTO.MyMatchDTO;
import Main.DTO.MyUserDTO;
import Main.exceptions.ApiExceptionResponse;
import Main.model.MyMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface MyMatchService {
    MyMatchDTO createMatch(MyMatchDTO match);
    MyMatchDTO findMatchByName(String name) throws ApiExceptionResponse;
    Page<MyMatchDTO> findAllMatches(Pageable page, String map, String username) throws ApiExceptionResponse;
    void deleteByName(String name) throws ApiExceptionResponse;
}
