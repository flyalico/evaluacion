package evaluacion.osp.pe.evaluacion.service;

import evaluacion.osp.pe.evaluacion.model.Turno;
import evaluacion.osp.pe.evaluacion.repository.TurnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TurnoService {
    private static final Logger log = Logger.getLogger(TurnoService.class.getName());
    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository){
        this.turnoRepository = turnoRepository;
    }
    public Turno create(Turno turno){
        return this.turnoRepository.save(turno);
    }
    public Turno findById(Long id){
        Turno turno = this.turnoRepository.findById(id).orElse(null);
        return turno;
    }
    public void delete(Long id){
        this.turnoRepository.deleteById(id);
    }
    public List<Turno> findAll(){
        return this.turnoRepository.findAll();
    }
}
