package evaluacion.osp.pe.evaluacion.model;

import evaluacion.osp.pe.evaluacion.model.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
