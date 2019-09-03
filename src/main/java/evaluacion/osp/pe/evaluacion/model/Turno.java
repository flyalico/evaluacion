package evaluacion.osp.pe.evaluacion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import evaluacion.osp.pe.evaluacion.model.Estado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Turno implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "no puede estar vacio")
    @JsonFormat(pattern = "HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP )
    private Date hora;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
