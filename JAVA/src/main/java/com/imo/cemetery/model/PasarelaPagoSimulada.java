package com.imo.cemetery.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * The type Pasarela pago simulada.
 */
@Component
@Slf4j
public class PasarelaPagoSimulada {

    /**
     * Procesar pago.
     *
     * @param importe the importe
     */
    public void procesarPago(BigDecimal importe) {
        log.info("Conectando con la pasarela para procesar el importe: {} €", importe);

        if (Math.random() < 0.2) {
            throw new RuntimeException("Transacción RECHAZADA por la entidad emisora.");
        }

        log.info("Pago autorizado con éxito.");
    }
}