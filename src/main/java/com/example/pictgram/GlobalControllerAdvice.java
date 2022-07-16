package com.example.pictgram;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Component
public class GlobalControllerAdvice {
/*
	@Autowired
	private MessageSource messageSource;
	
	protected static Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler (Exception e, Model model, Locale locale) {
		
		log.error(e.getMessage(), e);
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-danger");
		model.addAttribute("message", messageSource.getMessage("common.1", new String[] {}, locale));
		
		return "layouts/complete";
	}
	*/
}
