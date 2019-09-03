package evaluacion.osp.pe.evaluacion.service;



import evaluacion.osp.pe.evaluacion.model.Pelicula;
import evaluacion.osp.pe.evaluacion.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
@Service
public class PeliculaService {
    private static final Logger log = Logger.getLogger(PeliculaService.class.getName());
    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository){
        this.peliculaRepository = peliculaRepository;
    }
    public Pelicula create(Pelicula pelicula){
        return this.peliculaRepository.save(pelicula);
    }
    public Pelicula findById(Long id){
        Pelicula pelicula = this.peliculaRepository.findById(id).orElse(null); //.findOne(id);
        return pelicula;
    }
    public List<Pelicula> findAll(){
        return this.peliculaRepository.findAll();
    }

    public void delete(Long id){
        this.peliculaRepository.deleteById(id);
    }
}
