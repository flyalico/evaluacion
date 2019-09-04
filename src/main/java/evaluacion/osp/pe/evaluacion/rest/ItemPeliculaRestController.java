package evaluacion.osp.pe.evaluacion.rest;

import evaluacion.osp.pe.evaluacion.model.ItemPelicula;
import evaluacion.osp.pe.evaluacion.service.ItemPeliculaService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itemsPeliculas")
public class ItemPeliculaRestController {
    private static final Logger log = Logger.getLogger(ItemPeliculaRestController.class.getName());
    private final ItemPeliculaService itemPeliculaService;

    public ItemPeliculaRestController(ItemPeliculaService itemPeliculaService){
        this.itemPeliculaService = itemPeliculaService;
    }
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ItemPelicula itemPelicula, BindingResult result){
        ItemPelicula itemPeliculaNew = null;
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
            itemPeliculaNew =itemPeliculaService.create(itemPelicula);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El itemPelicula ha sido creado con exito");
        response.put("itemPelicula", itemPeliculaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @PutMapping(value="/{id}")
    public ResponseEntity<?> modify(@Valid @RequestBody ItemPelicula itemPelicula, BindingResult result, @PathVariable Long id){
        ItemPelicula itemPeliculaActual = itemPeliculaService.findById(id);
        ItemPelicula itemPeliculaUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo "+ err.getField()+" "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(itemPeliculaActual == null){
            response.put("mensaje","Error: no se pudo editar, el itemPelicula ID: ".concat(id.toString().concat("no existe en la Base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            itemPeliculaActual.setTurno(itemPelicula.getTurno());
            itemPeliculaUpdated = itemPeliculaService.create(itemPeliculaActual);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el itemPelicula en la base de datos");
            response.put("error ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El itemPelicula ha sido actualizado con exito!");
        response.put("itemPelicula", itemPeliculaUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            this.itemPeliculaService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el itemPelicula de la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El itemPelicula ha sido eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
}
