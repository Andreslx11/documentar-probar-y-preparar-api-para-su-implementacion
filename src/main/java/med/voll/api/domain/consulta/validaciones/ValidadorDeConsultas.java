package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;



// Con esto tenemos el metodo queremos implementar en cada uno de los validadores
// para conseguir implementar esta interface en cada una de las clases validaciones
// se va cada clase y implementa implements  ValidadorDeConsultar

public interface ValidadorDeConsultas {

    // Esta es la firma que utilizamos en nuestras vadilaciones
    public void validar(DatosAgendarConsulta datos);
}
