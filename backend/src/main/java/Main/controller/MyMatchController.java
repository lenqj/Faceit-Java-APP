package Main.controller;

import Main.DTO.MyMatchDTO;
import Main.constants.Map;
import Main.exceptions.ApiExceptionResponse;
import Main.model.MyMatch;
import Main.model.MyUser;
import Main.service.MyMatchService;
import Main.service.MyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost")
@Controller
@AllArgsConstructor
@RequestMapping("/match")
@Tag(name = "Match", description = "Match API")
@Validated
public class MyMatchController {
    private final MyMatchService myMatchService;

    @Operation(
            summary = "Find all matches",
            description = "Retrieve all matches with optional filtering by map"
    )
    @GetMapping
    public ResponseEntity findAllMatches(@PageableDefault Pageable pageable, @RequestParam(required = false) String map) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myMatchService.findAllMatches(pageable, map, null));
    }
    @Operation(
            summary = "Find match by name",
            description = "Retrieve match information based on the name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match found"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity findMatchByName(@PathVariable String name) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myMatchService.findMatchByName(name));
    }
    @Operation(
            summary = "Create match",
            description = "Create a new match with the provided details"
    )
    @PostMapping
    public ResponseEntity createMatch(@Valid @RequestBody MyMatchDTO myMatch) throws ApiExceptionResponse {
        return ResponseEntity.status(HttpStatus.OK).body(myMatchService.createMatch(myMatch));
    }
    @Operation(
            summary = "Delete match",
            description = "Delete a match based on the name"
    )
    @DeleteMapping("/{name}")
    public ResponseEntity deleteMatchByName(@PathVariable String name) throws ApiExceptionResponse {
        myMatchService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(name);
    }
    @Operation(
            summary = "Find all maps",
            description = "Retrieve all available maps"
    )
    @GetMapping("maps")
    public ResponseEntity findAllMaps(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.allValues());
    }
}
