package com.aspect.workorder.utility;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class GenericExceptionHandlerTest {

	@Autowired
	private GenericExceptionHandler handler;

	private MethodArgumentNotValidException exception1;
	
	@Before
	public void setUp() {
		exception1 = new MethodArgumentNotValidException(null, null);
	}

	

	@Test
	public void testHandleMethodArgumentNotValidMethodArgumentNotValidExceptionHttpHeadersHttpStatusWebRequest() {
		exception1 = Mockito.mock(MethodArgumentNotValidException.class);
		
		final BindingResult bindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(exception1.getBindingResult()).thenReturn(bindingResult);
		
		final FieldError  fieldError = Mockito.mock(FieldError.class);
		Mockito.when(exception1.getBindingResult().getFieldError()).thenReturn(fieldError );
		
		Mockito.when(exception1.getBindingResult().getFieldError().getField()).thenReturn("");
		Mockito.when(exception1.getBindingResult().getFieldError().getDefaultMessage()).thenReturn("");
		
		
		ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception1, null, null, null);
		
		SoftAssertions softly = new SoftAssertions();
		softly.assertThat(response).isNotNull();

		softly.assertAll();
	}

}
