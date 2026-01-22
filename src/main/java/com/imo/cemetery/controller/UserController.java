package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.user.UserCreateDTO;
import com.imo.cemetery.model.dto.user.UserResponseDTO;
import com.imo.cemetery.service.users.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // Permite entrar desde cualquier puerto distinto al de la aplicación
@RestController
@RequestMapping("controller/users")
@RequiredArgsConstructor
@Slf4j // Permite usar el objeto log para crear mensajes en consola
public class UserController {

    private final UserServiceImpl service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        List<UserResponseDTO> users = this.service.findAll();

        return ResponseEntity.ok(users); // Envía un 200 y construye el body con users
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto) { // @Valid se encarga de interceptar la peticion y aplicarlelos validators del dto
        //@RequestBody se encarga de decirle que coja el JSON del body de la peticion POST y lo convierta a UserCreateDTO
        UserResponseDTO response = service.create(dto);
        // Devolvemos 201 porque se ha creado un nuevo recurso
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // HttpStatus.CREATED envía un código 201 en el header y .build crea el cuerpo de la respuesta en el que incluye el UserResponseDTO
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build(); //Devuelve un 204 que significa que ha sido exitoso pero que no hay nada que devolver al ser null
        // el .build se encarga de que, como la ResponseEntity contiene un void, no hay nada que devolver realmente, no se puede construir la respuesta con .body
        // así que se encarga de indicar que ya ha acabado la configuracion de la respuesta y que la construya
    }

}
