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
        // Arrange
        int pagina = 0;
        int tamaño = 10;
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by("fecha").descending());
        List<Resena> resenas = Arrays.asList(
            Resena.builder().id(1L).texto("Excelente").puntuacion(5).fecha(LocalDate.now()).build(),
            Resena.builder().id(2L).texto("Buena").puntuacion(4).fecha(LocalDate.now()).build()
        );
        Page<Resena> paginaEsperada = new PageImpl<>(resenas, pageable, resenas.size());
        
        when(resenaRepositorio.findAll(pageable)).thenReturn(paginaEsperada);

        // Act
        Page<Resena> resultado = resenaServicio.obtenerResenasPaginadas(pagina, tamaño);

        // Assert
        assertEquals(2, resultado.getContent().size());
        verify(resenaRepositorio, times(1)).findAll(pageable);
    }

    @Test
    void obtenerTodasResenas_debeRetornarLista() {
        // Arrange
        List<Resena> resenas = Arrays.asList(
            Resena.builder().id(1L).texto("Excelente").puntuacion(5).fecha(LocalDate.now()).build(),
            Resena.builder().id(2L).texto("Regular").puntuacion(3).fecha(LocalDate.now()).build()
        );
        
        when(resenaRepositorio.findAll()).thenReturn(resenas);

        // Act
        List<Resena> resultado = resenaServicio.obtenerTodasResenas();

        // Assert
        assertEquals(2, resultado.size());
        verify(resenaRepositorio, times(1)).findAll();
    }

    @Test
    void obtenerResenaPorId_CuandoExiste_debeRetornarResena() throws ResourceNotFoundException {
        // Arrange
        Long id = 1L;
        Resena resena = Resena.builder()
            .id(id)
            .texto("Buena")
            .puntuacion(4)
            .fecha(LocalDate.now())
            .build();
        
        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resena));

        // Act
        Resena resultado = resenaServicio.obtenerResenaPorId(id).getBody();

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(resenaRepositorio, times(1)).findById(id);
    }

    @Test
    void obtenerResenaPorId_CuandoNoExiste_debeLanzarExcepcion() {
        // Arrange
        Long id = 99L;
        
        when(resenaRepositorio.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            resenaServicio.obtenerResenaPorId(id);
        });
    }

    @Test
    void crearResena_debeRetornarResenaGuardada() {
        // Arrange
        Resena resena = Resena.builder()
            .texto("Mala")
            .puntuacion(2)
            .fecha(LocalDate.now())
            .build();
            
        Resena resenaGuardada = Resena.builder()
            .id(1L)
            .texto("Mala")
            .puntuacion(2)
            .fecha(LocalDate.now())
            .build();
        
        when(resenaRepositorio.save(resena)).thenReturn(resenaGuardada);

        // Act
        Resena resultado = resenaServicio.crearResena(resena);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId());
        verify(resenaRepositorio, times(1)).save(resena);
    }

    @Test
    void actualizarResena_debeRetornarResenaActualizada() throws ResourceNotFoundException {
        // Arrange
        Long id = 1L;
        Resena resenaExistente = Resena.builder()
            .id(id)
            .texto("Original")
            .puntuacion(3)
            .fecha(LocalDate.now())
            .build();
            
        Resena nuevosDatos = Resena.builder()
            .texto("Actualizada")
            .puntuacion(5)
            .fecha(LocalDate.now())
            .build();
        
        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resenaExistente));
        when(resenaRepositorio.save(resenaExistente)).thenReturn(resenaExistente);

        // Act
        Resena resultado = resenaServicio.actualizarResena(id, nuevosDatos).getBody();

        // Assert
        assertEquals("Actualizada", resultado.getTexto());
        assertEquals(5, resultado.getPuntuacion());
        verify(resenaRepositorio, times(1)).findById(id);
        verify(resenaRepositorio, times(1)).save(resenaExistente);
    }

    @Test
    void eliminarResena_debeEliminarCorrectamente() throws ResourceNotFoundException {
        // Arrange
        Long id = 1L;
        Resena resena = Resena.builder()
            .id(id)
            .texto("A eliminar")
            .puntuacion(1)
            .fecha(LocalDate.now())
            .build();
        
        when(resenaRepositorio.findById(id)).thenReturn(Optional.of(resena));
        doNothing().when(resenaRepositorio).delete(resena);

        // Act & Assert
        assertDoesNotThrow(() -> {
            resenaServicio.eliminarResena(id);
        });
        
        verify(resenaRepositorio, times(1)).findById(id);
        verify(resenaRepositorio, times(1)).delete(resena);
    }
}
