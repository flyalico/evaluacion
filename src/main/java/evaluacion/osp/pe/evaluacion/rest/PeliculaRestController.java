package evaluacion.osp.pe.evaluacion.rest ;



import evaluacion.osp.pe.evaluacion.model.Pelicula;
import evaluacion.osp.pe.evaluacion.service.PeliculaService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaRestController {
    private final PeliculaService peliculaService;

    public PeliculaRestController(PeliculaService peliculaService){
        this.peliculaService = peliculaService;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody Pelicula pelicula, BindingResult result){
        Pelicula peliculaNew = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(err->"El campo '"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors ",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            peliculaNew =peliculaService.create(pelicula);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El pelicula ha sido creado con exito");
        response.put("pelicula", peliculaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        /*Pelicula peliculaCreated = this.peliculaService.create(pelicula);
        URI uri = MvcUriComponentsBuilder.fromController(PeliculaRestController.class).path("/" + peliculaCreated.getId())
                .build().toUri();
        return  ResponseEntity.created(uri).build();*/
    }
    @PutMapping
    public Pelicula modify(@RequestBody Pelicula pelicula){
        return this.peliculaService.create(pelicula);
    }

    @GetMapping
    public List<Pelicula> list(){
        return this.peliculaService.findAll();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> one(@PathVariable Long id){

        //return this.peliculaService.findById(id);
        Pelicula pelicula = null;
        Map<String, Object> response = new HashMap<>();
        try{
            pelicula = peliculaService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(pelicula == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pelicula>(pelicula, HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            this.peliculaService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar la pelicula de la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La pelicula ha sido eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
}
