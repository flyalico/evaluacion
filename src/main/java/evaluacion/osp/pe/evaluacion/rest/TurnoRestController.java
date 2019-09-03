package evaluacion.osp.pe.evaluacion.rest;


import evaluacion.osp.pe.evaluacion.model.Pelicula;
import evaluacion.osp.pe.evaluacion.model.Turno;
import evaluacion.osp.pe.evaluacion.service.TurnoService;
import org.springframework.dao.DataAccessException;
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
@RequestMapping("/api/turnos")
public class TurnoRestController {
    private final TurnoService turnoService;
    public TurnoRestController(TurnoService turnoService){
        this.turnoService = turnoService;
    }
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody Turno turno, BindingResult result){
        Turno turnoNew = null;
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
            turnoNew =turnoService.create(turno);
        }catch(DataAccessException e){
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El turno ha sido creado con exito");
        response.put("turno", turnoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
    @PutMapping(value="/{id}")
    public ResponseEntity<?> modify(@Valid @RequestBody Turno turno, BindingResult result, @PathVariable Long id){
        Turno turnoActual = turnoService.findById(id);
        Turno turnoUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors= result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo "+ err.getField()+" "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(turnoActual == null){
            response.put("mensaje","Error: no se pudo editar, el pelicula ID: ".concat(id.toString().concat("no existe en la Base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            turnoActual.setHora(turno.getHora());
            turnoActual.setEstado(turno.getEstado());
           // turnoActual.setFecha(pelicula.getFecha());
            turnoUpdated = turnoService.create(turnoActual);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al actualizar el turno en la base de datos");
            response.put("error ", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El turno ha sido actualizado con exito!");
        response.put("turno", turnoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @GetMapping(value="/{id}")
    public ResponseEntity<?> one(@PathVariable Long id){

        Turno turno = null;
        Map<String, Object> response = new HashMap<>();
        try{
            turno = turnoService.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(turno == null){
            response.put("mensaje", "El turno ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Turno>(turno, HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            this.turnoService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el turno de la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El turno ha sido eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
    @GetMapping
    public List<Turno> list(){
        return this.turnoService.findAll();
    }
}
