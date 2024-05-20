package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.Request;
import com.shinobicoders.teamcodeapi.model.RequestStatus;
import com.shinobicoders.teamcodeapi.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @InjectMocks
    private RequestService requestService;

    @Mock
    private RequestRepository requestRepository;

    private Request requestMock;

    @BeforeEach
    void setUp() {
        requestMock = new Request();
        requestMock.setId(1L);
        requestMock.setStatus(RequestStatus.PENDING);
        requestMock.setMessage("Test message");
        requestMock.setRequestDate(new Date());
        requestMock.setUser(null);
        requestMock.setProject(null);
    }

    @Nested
    class GetAllRequestsTest {
        @Test
        void shouldReturnAllRequests() {
            when(requestRepository.findAll()).thenReturn(List.of(requestMock));

            List<Request> result = requestService.getAllRequests();

            assertEquals(1, result.size());
            assertEquals(requestMock, result.get(0));
            verify(requestRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnEmptyList() {
            when(requestRepository.findAll()).thenReturn(List.of());

            List<Request> result = requestService.getAllRequests();

            assertEquals(0, result.size());
            verify(requestRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetRequestByIdTest {
        @Test
        void shouldReturnRequestById() {
            when(requestRepository.findById(1L)).thenReturn(java.util.Optional.of(requestMock));

            Request result = requestService.getRequestById(1L);

            assertEquals(requestMock, result);
            verify(requestRepository, times(1)).findById(1L);
        }

        @Test
        void shouldReturnNull() {
            when(requestRepository.findById(1L)).thenReturn(Optional.empty());

            Request result = requestService.getRequestById(1L);

            assertNull(result);
            verify(requestRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class GetRequestsByUserIdTest {
        @Test
        void shouldReturnRequestsByUserId() {
            when(requestRepository.findAllByUserId(1L)).thenReturn(List.of(requestMock));

            List<Request> result = requestService.getRequestsByUserId(1L);

            assertEquals(1, result.size());
            assertEquals(requestMock, result.get(0));
            verify(requestRepository, times(1)).findAllByUserId(1L);
        }

        @Test
        void shouldReturnEmptyList() {
            when(requestRepository.findAllByUserId(1L)).thenReturn(List.of());

            List<Request> result = requestService.getRequestsByUserId(1L);

            assertEquals(0, result.size());
            verify(requestRepository, times(1)).findAllByUserId(1L);
        }
    }

    @Nested
    class GetRequestsByProjectIdTest {
        @Test
        void shouldReturnRequestsByProjectId() {
            when(requestRepository.findAllByProjectId(1L)).thenReturn(List.of(requestMock));

            List<Request> result = requestService.getRequestsByProjectId(1L);

            assertEquals(1, result.size());
            assertEquals(requestMock, result.get(0));
            verify(requestRepository, times(1)).findAllByProjectId(1L);
        }

        @Test
        void shouldReturnEmptyList() {
            when(requestRepository.findAllByProjectId(1L)).thenReturn(List.of());

            List<Request> result = requestService.getRequestsByProjectId(1L);

            assertEquals(0, result.size());
            verify(requestRepository, times(1)).findAllByProjectId(1L);
        }
    }

    @Nested
    class CreateRequestTest {
        @Test
        void shouldCreateRequest() {
            when(requestRepository.save(requestMock)).thenReturn(requestMock);

            Request result = requestService.createRequest(requestMock);

            assertEquals(requestMock, result);
            verify(requestRepository, times(1)).save(requestMock);
        }
    }

    @Nested
    class UpdateRequestTest {
        @Test
        void shouldUpdateRequest() {
            Request updatedRequest = new Request();
            updatedRequest.setStatus(RequestStatus.APPROVED);
            updatedRequest.setMessage("Updated message");

            when(requestRepository.findById(1L)).thenReturn(Optional.of(requestMock));
            when(requestRepository.save(requestMock)).thenReturn(requestMock);

            Request result = requestService.updateRequest(1L, updatedRequest);

            assertEquals(RequestStatus.APPROVED, result.getStatus());
            assertEquals("Updated message", result.getMessage());
            verify(requestRepository, times(1)).findById(1L);
            verify(requestRepository, times(1)).save(requestMock);
        }

        @Test
        void shouldReturnNull() {
            Request updatedRequest = new Request();
            updatedRequest.setStatus(RequestStatus.APPROVED);
            updatedRequest.setMessage("Updated message");

            when(requestRepository.findById(1L)).thenReturn(Optional.empty());

            Request result = requestService.updateRequest(1L, updatedRequest);

            assertNull(result);
            verify(requestRepository, times(1)).findById(1L);
            verify(requestRepository, times(0)).save(requestMock);
        }
    }

    @Nested
    class DeleteRequestTest {
        @Test
        void shouldDeleteRequest() {
            doNothing().when(requestRepository).deleteById(1L);

            requestService.deleteRequest(1L);

            verify(requestRepository, times(1)).deleteById(1L);
        }
    }
}
