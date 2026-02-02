package com.imo.cemetery.service.difunto;

import com.imo.cemetery.model.PasarelaPagoSimulada;
import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cemetery.model.entity.*;
import com.imo.cemetery.model.enums.EstadoType;
import com.imo.cemetery.model.enums.PagoEstadoType;
import com.imo.cemetery.model.enums.PagoType;
import com.imo.cemetery.model.enums.ServicioType;
import com.imo.cemetery.model.mapper.DifuntoMapper;
import com.imo.cemetery.model.mapper.ImplementacionServicioMapper;
import com.imo.cemetery.repository.*;
import com.imo.cemetery.service.implementacionService.ImplementacionServicioService;
import com.imo.cemetery.service.pago.PagoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifuntoServiceImpl implements DifuntoService {

    private final DifuntoRepository repo;
    private final ParcelaRepository parcelaRepository;
    private final DifuntoMapper difuntoMapper;
    private final PagoService pagoService;
    private final PasarelaPagoSimulada pasarela;
    private final ServicioRepository servicioRepository;
    private final ImplementacionServicioService implementacionServicioService;
    private final FacturacionRepository facturacionRepo;
    private final PagoRepository pagoRepository;



    @Override
    @Transactional
    public DifuntoResponseDTO create(DifuntoCreateDTO dto) {

        // Buscar el servicio
        Servicio servicioInhumacion = servicioRepository.findByTipo(ServicioType.INHUMAR)
                .orElseThrow(() -> new EntityNotFoundException("Servicio INHUMAR no configurado"));


        pasarela.procesarPago(servicioInhumacion.getPrecio());


        Parcela parcela = parcelaRepository.findById(dto.getParcelaId())
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));


        Cliente cliente = parcela.getConcesion().getCliente();


        Facturacion facturacion = Facturacion.builder()
                .dni(cliente.getDni())
                .nombre(cliente.getNombre())
                .apellido1(cliente.getApellido1())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .importe((servicioInhumacion.getPrecio()))
                .build();

        Facturacion facturacionGuardada = facturacionRepo.save(facturacion);


        ImplementacionServicioCreateDTO impDTO = ImplementacionServicioCreateDTO.builder()
                .fechaRealizacion(LocalDate.now())
                .parcelaId(parcela.getId())
                .servicioId(servicioInhumacion.getId())
                .facturacionId(facturacionGuardada.getId())
                .build();

        implementacionServicioService.create(impDTO);

        registrarPagoYVincularFactura(facturacionGuardada, parcela.getConcesion());


        Difunto entity = difuntoMapper.toEntity(dto);
        entity.setParcela(parcela);
        parcela.setEstado(EstadoType.OCUPADA);
        parcelaRepository.save(parcela);

        return difuntoMapper.toResponseDTO(repo.save(entity));
    }


    private void registrarPagoYVincularFactura(Facturacion facturacion, Concesion concesion) {

        Pago pago = Pago.builder()
                .importe(facturacion.getImporte())
                .fecha(LocalDate.now())
                .concesion(concesion)
                .estado(PagoEstadoType.APROBADO)
                .metodo(PagoType.VISA)
                .transaccionId("SIM-" + java.util.UUID.randomUUID().toString().substring(0, 8))
                .build();

        Pago pagoGuardado = pagoRepository.save(pago);


        facturacion.setPago(pagoGuardado);
        facturacionRepo.save(facturacion);
    }

    @Override
    @Transactional
    public void exhumar(Long difuntoId) {
        Difunto difunto = repo.findById(difuntoId)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));

        Parcela parcela = difunto.getParcela();


        Servicio servicioExhumar = servicioRepository.findByTipo(ServicioType.EXHUMAR)
                .orElseThrow(() -> new EntityNotFoundException("Servicio EXHUMAR no configurado"));


        pasarela.procesarPago(servicioExhumar.getPrecio());


        Cliente cliente = parcela.getConcesion().getCliente();
        Facturacion facturacion = Facturacion.builder()
                .dni(cliente.getDni())
                .nombre(cliente.getNombre())
                .apellido1(cliente.getApellido1())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .importe(servicioExhumar.getPrecio())
                .build();

        Facturacion facturacionGuardada = facturacionRepo.save(facturacion);


        ImplementacionServicioCreateDTO impDTO = ImplementacionServicioCreateDTO.builder()
                .fechaRealizacion(LocalDate.now())
                .parcelaId(parcela.getId())
                .servicioId(servicioExhumar.getId())
                .facturacionId(facturacionGuardada.getId())
                .build();

        implementacionServicioService.create(impDTO);


        registrarPagoYVincularFactura(facturacionGuardada, parcela.getConcesion());


        parcela.setEstado(EstadoType.RESERVADA);
        parcelaRepository.save(parcela);


        repo.delete(difunto);
    }

    @Override
    @Transactional
    public DifuntoResponseDTO update(Long id, DifuntoUpdateDTO dto) {

        DifuntoResponseDTO response;

        Difunto entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));


        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getApellido1() != null) entity.setApellido1(dto.getApellido1());
        if (dto.getApellido2() != null) entity.setApellido2(dto.getApellido2());
        if (dto.getYearNacimiento() != null) entity.setYearNacimiento(dto.getYearNacimiento());
        if (dto.getYearDefuncion() != null) entity.setYearDefuncion(dto.getYearDefuncion());
        if (dto.getFechaEntierro() != null) entity.setFechaEntierro(dto.getFechaEntierro());
        if (dto.getMensaje() != null) entity.setMensaje(dto.getMensaje());
        if (dto.getFoto() != null) entity.setFoto(dto.getFoto());


        if (dto.getParcelaId() != null && !dto.getParcelaId().equals(entity.getParcela().getId())) {
            Parcela nuevaParcela = parcelaRepository.findById(dto.getParcelaId())
                    .orElseThrow(() -> new EntityNotFoundException("Nueva parcela no encontrada"));


            entity.setParcela(nuevaParcela);
            nuevaParcela.setEstado(EstadoType.OCUPADA);
            parcelaRepository.save(nuevaParcela);
        }

        response = difuntoMapper.toResponseDTO(repo.save(entity));


        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Difunto entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DifuntoResponseDTO findById(Long id) {
        DifuntoResponseDTO response = repo.findById(id)
                .map(difuntoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAll() {
        List<DifuntoResponseDTO> response = repo.findAll()
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findByFullName(String nombre, String ape1, String ape2) {
        List<DifuntoResponseDTO> response = repo.findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(nombre, ape1, ape2)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByParcela(Long parcelaId) {
        List<DifuntoResponseDTO> response = repo.findAllByParcelaId(parcelaId)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByYearDefuncion(Year year) {
        List<DifuntoResponseDTO> response = repo.findAllByYearDefuncion(year)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByAyuntamientoId(Long id)
    {
        List<DifuntoResponseDTO> response = repo.findAllByParcelaZonaCementerioAyuntamientoId((id))
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByCementerioId(Long id)
    {
        List<DifuntoResponseDTO> response = repo.findAllByParcelaZonaCementerioId((id))
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByCliente(Long id)
    {
        return repo.findAllByParcelaConcesionClienteId(id)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByClienteAndAyuntamiento(Long clienteId, Long aytoId) {
        return repo.findDifuntosByClienteAndAyuntamiento(clienteId, aytoId)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
    }
}