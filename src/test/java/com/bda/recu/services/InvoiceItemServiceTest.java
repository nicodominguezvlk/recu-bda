package com.bda.recu.services;

import com.bda.recu.dtos.InvoiceItemDTO;
import com.bda.recu.models.*;
import com.bda.recu.repos.*;
import com.bda.recu.services.mappers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceItemServiceTest {

    private InvoiceItemService invoiceItemService;
    private InvoiceItemRepository invoiceItemRepository;
    private InvoiceRepository invoiceRepository;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    private TrackRepository trackRepository;
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private GenreRepository genreRepository;
    private MediaTypeRepository mediaTypeRepository;


    @BeforeEach
    public void setup(){
        // Employee
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeMapper employeeMapper = new EmployeeMapper(employeeRepository);
        EmployeeDTOMapper employeeDTOMapper = new EmployeeDTOMapper();
        EmployeeService employeeService = new EmployeeService(employeeRepository, employeeMapper, employeeDTOMapper);

        // Customer
        customerRepository = Mockito.mock(CustomerRepository.class);
        CustomerMapper customerMapper = new CustomerMapper(employeeService);
        CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();
        CustomerService customerService = new CustomerService(customerRepository, customerMapper, customerDTOMapper);

        // Artist
        artistRepository = Mockito.mock(ArtistRepository.class);
        ArtistMapper artistMapper = new ArtistMapper();
        ArtistDTOMapper artistDTOMapper = new ArtistDTOMapper();
        ArtistService artistService = new ArtistService(artistRepository, artistMapper, artistDTOMapper);

        // Album
        albumRepository = Mockito.mock(AlbumRepository.class);
        AlbumMapper albumMapper = new AlbumMapper(artistService);
        AlbumDTOMapper albumDTOMapper = new AlbumDTOMapper();
        AlbumService albumService = new AlbumService(albumRepository, albumMapper, albumDTOMapper);

        // Genre
        genreRepository = Mockito.mock(GenreRepository.class);
        GenreMapper genreMapper = new GenreMapper();
        GenreDTOMapper genreDTOMapper = new GenreDTOMapper();
        GenreService genreService = new GenreService(genreRepository, genreMapper, genreDTOMapper);

        // MediaType
        mediaTypeRepository = Mockito.mock(MediaTypeRepository.class);
        MediaTypeMapper mediaTypeMapper = new MediaTypeMapper();
        MediaTypeDTOMapper mediaTypeDTOMapper = new MediaTypeDTOMapper();
        MediaTypeService mediaTypeService = new MediaTypeService(mediaTypeRepository, mediaTypeMapper, mediaTypeDTOMapper);

        // Track
        trackRepository = Mockito.mock(TrackRepository.class);
        TrackMapper trackMapper = new TrackMapper(albumService, mediaTypeService, genreService);
        TrackDTOMapper trackDTOMapper = new TrackDTOMapper();
        FilteredTrackDTOMapper filteredTrackDTOMapper = new FilteredTrackDTOMapper();
        TrackService trackService = new TrackService(trackRepository, trackMapper, trackDTOMapper, filteredTrackDTOMapper, artistService);

        // Invoice
        invoiceRepository = Mockito.mock(InvoiceRepository.class);
        InvoiceMapper invoiceMapper = new InvoiceMapper(customerService);
        InvoiceDTOMapper invoiceDTOMapper = new InvoiceDTOMapper();
        InvoiceService invoiceService = new InvoiceService(invoiceRepository, invoiceMapper, invoiceDTOMapper, customerService, trackService, invoiceItemService, invoiceItemRepository, invoiceItemMapper);

        // InvoiceItem
        invoiceItemRepository = Mockito.mock(InvoiceItemRepository.class);
        InvoiceItemMapper invoiceItemMapper = new InvoiceItemMapper(invoiceService, trackService);
        InvoiceItemDTOMapper invoiceItemDTOMapper = new InvoiceItemDTOMapper();
        invoiceItemService = new InvoiceItemService(invoiceItemRepository, invoiceItemMapper, invoiceItemDTOMapper);
    }

    @Test
    public void testAdd(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(1, 1, 1, 50.00, 2);

        // Act
        InvoiceItemDTO result = invoiceItemService.add(invoiceItemDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceItemDTO.getInvoiceLineId(), result.getInvoiceLineId());
        assertEquals(invoiceItemDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceItemDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testUpdate(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO(1, 1, 1, 50.00, 2);
        invoiceItemService.add(invoiceItemDTO);
        invoiceItemDTO = new InvoiceItemDTO(1, 1, 1, 50.00, 3);

        // Act
        InvoiceItemDTO result = invoiceItemService.update(invoiceItemDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceItemDTO.getInvoiceLineId(), result.getInvoiceLineId());
        assertEquals(invoiceItemDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceItemDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testDeleteValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItem invoiceItem = new InvoiceItem(1, invoice, track, 50.00, 2);
        Mockito.when(invoiceItemRepository.findById(1)).thenReturn(Optional.of(invoiceItem));

        // Act
        InvoiceItemDTO result = invoiceItemService.delete(1);
        InvoiceItemDTO invoiceItemDTO = invoiceItemService.map(invoiceItem);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceItemDTO.getInvoiceLineId(), result.getInvoiceLineId());
        assertEquals(invoiceItemDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceItemDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void testDeleteNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItem invoiceItem = new InvoiceItem(1, invoice, track, 50.00, 2);

        // Act
        InvoiceItemDTO result = invoiceItemService.delete(2);

        // Assert
        assertNull(result);
    }

    @Test
    public void getByIdValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItem invoiceItem = new InvoiceItem(1, invoice, track, 50.00, 2);
        Mockito.when(invoiceItemRepository.findById(1)).thenReturn(Optional.of(invoiceItem));

        // Act
        InvoiceItemDTO result = invoiceItemService.getById(1);
        InvoiceItemDTO invoiceItemDTO = invoiceItemService.map(invoiceItem);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceItemDTO.getInvoiceLineId(), result.getInvoiceLineId());
        assertEquals(invoiceItemDTO.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoiceItemDTO.getQuantity(), result.getQuantity());
    }

    @Test
    public void getByIdNotValid(){
        // Arrange
        Genre genre = new Genre(1, "Trance", new ArrayList<>());
        Mockito.when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        MediaType mediaType = new MediaType(1, "MP3", new ArrayList<>());
        Mockito.when(mediaTypeRepository.findById(1)).thenReturn(Optional.of(mediaType));
        Artist artist = new Artist(1, "deadmau5", new ArrayList<>());
        Mockito.when(artistRepository.findById(1)).thenReturn(Optional.of(artist));
        Album album = new Album(1, "4x4 = 12", artist, new ArrayList<>());
        Mockito.when(albumRepository.findById(1)).thenReturn(Optional.of(album));
        Track track = new Track(1, "cabin", album, mediaType, genre, "Zimmerman", 200000, 300000, 50.00, new ArrayList<>(), new ArrayList<>());
        Mockito.when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        Employee employee = new Employee(1, "Clinton", "Franklin", "CEO", null, LocalDateTime.now(), LocalDateTime.now(), "Vinewood", "Los Santos", "San Andreas", "US", "75000", "987654321", "", "frankieclinton@gmail.com", new ArrayList<>());
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Customer customer = new Customer(1, "Michael", "De Santa", "Trevor Philip's Industries", "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", "123456789", "", "mikedesanta@gmail.com", employee, new ArrayList<>());
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Invoice invoice = new Invoice(1, customer, LocalDateTime.now(), "Rockford Hills", "Los Santos", "San Andreas", "US", "75000", 100.00, new ArrayList<>());
        Mockito.when(invoiceRepository.findById(1)).thenReturn(Optional.of(invoice));
        InvoiceItem invoiceItem = new InvoiceItem(1, invoice, track, 50.00, 2);

        // Act
        InvoiceItemDTO result = invoiceItemService.getById(2);

        // Assert
        assertNull(result);
    }
}