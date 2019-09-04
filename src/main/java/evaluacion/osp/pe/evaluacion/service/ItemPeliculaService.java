package evaluacion.osp.pe.evaluacion.service;

import evaluacion.osp.pe.evaluacion.model.ItemPelicula;
import evaluacion.osp.pe.evaluacion.repository.ItemPeliculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.logging.Logger;

@Service
public class ItemPeliculaService {
    private static final Logger log = Logger.getLogger(TurnoService.class.getName());
    private final ItemPeliculaRepository itemPeliculaRepository;

    public ItemPeliculaService(ItemPeliculaRepository itemPeliculaRepository){
        this.itemPeliculaRepository = itemPeliculaRepository;
    }
    @Transactional
    public ItemPelicula create(ItemPelicula itemPelicula){
        return this.itemPeliculaRepository.save(itemPelicula);
    }

    @Transactional
    public void delete(Long id){
        this.itemPeliculaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ItemPelicula findById(Long id){
        return this.itemPeliculaRepository.findById(id).orElse(null);
    }
}
