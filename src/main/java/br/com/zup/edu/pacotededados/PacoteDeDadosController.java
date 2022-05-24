package br.com.zup.edu.pacotededados;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class PacoteDeDadosController {

    private final PacoteDeDadosRepository pacoteDeDadosRepository;

    public PacoteDeDadosController(PacoteDeDadosRepository pacoteDeDadosRepository) {
        this.pacoteDeDadosRepository = pacoteDeDadosRepository;
    }

    @PostMapping("/pacote")
    public ResponseEntity<?> cadastra(@RequestBody @Valid PacoteDeDadosRequest pacoteDeDadosRequest,
                                      UriComponentsBuilder uriComponentsBuilder){
        String hashCpf = CPFUtils.hash(pacoteDeDadosRequest.getCpf());

        if(pacoteDeDadosRepository.existsByHashCpf(hashCpf)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já existente");
        }

        PacoteDeDados pacoteDeDados = pacoteDeDadosRequest.toModel();
        pacoteDeDadosRepository.save(pacoteDeDados);

        URI location = uriComponentsBuilder.path("/pacote/{id}").buildAndExpand(pacoteDeDados.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> errorUniqueConstraint(ConstraintViolationException e, WebRequest request){
        Map<String, Object> body = Map.of(
                "code", 422,
                "status", "UNPROCESSABLE_ENTITY",
                "timestamp", LocalDateTime.now(),
                "path", request.getDescription(false).replace("uri=",""),
                "message", "CPF já existente!!!"
        );
        return ResponseEntity.unprocessableEntity().body(body);
    }
}
