package com.atos.concesionario.proyecto_concesionario.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.atos.concesionario.proyecto_concesionario.Exception.ResourceNotFoundException;
import com.atos.concesionario.proyecto_concesionario.Model.Resena;
import com.atos.concesionario.proyecto_concesionario.Repository.ResenaRepositorio;

@ExtendWith(MockitoExtension.class)
class ResenaServicioTest {

    @Mock
    private ResenaRepositorio resenaRepositorio;

    @InjectMocks
    private ResenaServicio resenaServicio;

    @Test
    void obtenerResenasPaginadas_debeRetornarPagina() {
        int pagina = 0;
        int tamaño = 10;
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by("fecha").descending());

        Resena resena1 = new Resena();
        resena1.setId(1L);
        resena1.setComentario("Excelente");
        resena1.setPuntuacion(5);
        resena1.setFecha(LocalDate.now());

        Resena resena2 = new Resena();
        resena2.setId(2L);
        resena2.setComentario("Buena");
        resena2.setPuntuacion(4);
        resena2.setFecha(LocalDate.now());

        List<Resena> resenas = Arrays.asList(resena1, resena2);
        Page<Resena> paginaEsperada = new PageImpl<>(resenas, pageable, resenas.size());

        when(resenaRepositorio.findAll(pageable)).thenReturn(paginaEsperada);

        Page<Resena> resultado = resenaServicio.obtenerResenasPaginadas(pagina, tamaño);

        assertEquals(2, resultado.getContent().size());
        verify(resenaRepositorio, times(1)).findAll(pageable);
    }

    @Test
    void obtenerTodasResenas_debeRetornarLista() {
        Resena resena1 = new Resena();
        resena1.setId(1L);
        resena1.setComentario("Excelente");
        resena1.setPuntuacion(5);
        resena1.setFecha(LocalDate.now());

        Resena resena2 = new Resena();
        resena2.setId(2L);
        resena2.setComentario("Regular");
        resena2.setPuntuacion(3);
        resena2.setFecha(LocalDate.now());

        List<Resena> resenas = Arrays.asList(resena1, resena2);

        when(resenaRepositorio.findAll()).thenReturn(resenas);

        List<Resena> resultado = resenaServicio.obtenerTodasResenas();

        assertEquals(2, resultado.size());
        verify(resenaRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerResenaPorId_CuandoExiste_debeRetornarResena() throws ResourceNotFoundException {
        Long id = 1L;
        Resena resena = new Resena();
        resena.setId(id);
        resena.setComentario("Buena");
        resena.setPuntuacion(4);
        resena.setFecha(LocalDate.now());

        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resena));

        Resena resultado = resenaServicio.obtenerResenaPorId(id).getBody();

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(resenaRepositorio, times(1)).findById(id);
    }

    @Test
    void obtenerResenaPorId_CuandoNoExiste_debeLanzarExcepcion() {
        Long id = 99L;

        when(resenaRepositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> resenaServicio.obtenerResenaPorId(id));
    }

    @Test
    void crearResena_debeRetornarResenaGuardada() {
        Resena resena = new Resena();
        resena.setComentario("Mala");
        resena.setPuntuacion(2);
        resena.setFecha(LocalDate.now());

        Resena resenaGuardada = new Resena();
        resenaGuardada.setId(1L);
        resenaGuardada.setComentario("Mala");
        resenaGuardada.setPuntuacion(2);
        resenaGuardada.setFecha(LocalDate.now());

        when(resenaRepositorio.save(resena)).thenReturn(resenaGuardada);

        Resena resultado = resenaServicio.crearResena(resena);

        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId());
        verify(resenaRepositorio, times(1)).save(resena);
    }

    @Test
    void actualizarResena_debeRetornarResenaActualizada() throws ResourceNotFoundException {
        Long id = 1L;

        Resena resenaExistente = new Resena();
        resenaExistente.setId(id);
        resenaExistente.setComentario("Original");
        resenaExistente.setPuntuacion(3);
        resenaExistente.setFecha(LocalDate.now());

        Resena nuevosDatos = new Resena();
        nuevosDatos.setComentario("Actualizada");
        nuevosDatos.setPuntuacion(5);
        nuevosDatos.setFecha(LocalDate.now());

        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resenaExistente));
        when(resenaRepositorio.save(resenaExistente)).thenReturn(resenaExistente);

        Resena resultado = resenaServicio.actualizarResena(id, nuevosDatos).getBody();

        assertNotNull(resultado);
        assertEquals("Actualizada", resultado.getComentario());
        assertEquals(5, resultado.getPuntuacion());
        verify(resenaRepositorio, times(1)).findById(id);
        verify(resenaRepositorio, times(1)).save(resenaExistente);
    }

    @Test
    void eliminarResena_debeEliminarCorrectamente() {
        Long id = 1L;

        Resena resena = new Resena();
        resena.setId(id);
        resena.setComentario("A eliminar");
        resena.setPuntuacion(1);
        resena.setFecha(LocalDate.now());

        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resena));
        doNothing().when(resenaRepositorio).delete(resena);

        assertDoesNotThrow(() -> resenaServicio.eliminarResena(id));

        verify(resenaRepositorio, times(1)).findById(id);
        verify(resenaRepositorio, times(1)).delete(resena);
    }
}
