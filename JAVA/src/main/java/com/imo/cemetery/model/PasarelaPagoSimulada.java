package com.imo.cemetery.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class PasarelaPagoSimulada {

    public void procesarPago(BigDecimal importe) {
        log.info("Conectando con la pasarela para procesar el importe: {} €", importe);

        if (Math.random() < 0.2) {
            throw new RuntimeException("Transacción RECHAZADA por la entidad emisora.");
        }

        log.info("Pago autorizado con éxito.");
    }
}