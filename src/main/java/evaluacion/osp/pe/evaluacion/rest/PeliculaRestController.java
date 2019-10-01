package evaluacion.osp.pe.evaluacion.rest ;



import evaluacion.osp.pe.evaluacion.model.Pelicula;
import evaluacion.osp.pe.evaluacion.service.PeliculaService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

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

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '"+err.getField()+"' " +err.getDefaultMessage())
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

    }
    @PutMapping(value="/{id}")
    public ResponseEntity<?> modify(@Valid @RequestBody Pelicula pelicula, BindingResult result, @PathVariable Long id){
        Pelicula peliculaActual = peliculaService.findById(id);
        Pelicula peliculaUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo "+ err.getField()+" "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(peliculaActual == null){
            response.put("mensaje","Error: no se pudo editar, el pelicula ID: ".concat(id.toString().concat("no existe en la Base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
           // peliculaActual.setNombre(pelicula.getNombre());
           // peliculaActual.setEstado(pelicula.getEstado());
           // peliculaActual.setFecha(pelicula.getFecha());
            peliculaUpdated = peliculaService.create(peliculaActual);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar al pelicula en la base de datos");
            response.put("error ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El pelicula ha sido actualizado con exito!");
        response.put("pelicula", peliculaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pelicula> list(){
        return this.peliculaService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Pelicula> listaPaginada(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return peliculaService.findAllPageable(pageable);
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
            response.put("mensaje", "El pelicula ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
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
