package com.ossorio.taller3.controller.implementation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossorio.taller3.model.Sympweightbyday;
import com.ossorio.taller3.service.interfaces.SymptomquestionService;
import com.ossorio.taller3.service.interfaces.SympweightbydayService;

@Controller
public class WeightControllerImpl {

	private final SympweightbydayService weightService;
	private final SymptomquestionService questionService;

	public WeightControllerImpl(SympweightbydayService weightService, SymptomquestionService questionService) {
		this.weightService = weightService;
		this.questionService = questionService;
	}

	@GetMapping("/weight")
	public String weightIndex(Model model) {
		model.addAttribute("weights", weightService.findAll());
		return "weight/index";
	}

	@GetMapping("/weight/add")
	public String weightAdd(Model model) {
		model.addAttribute("sympweightbyday", new Sympweightbyday());
		model.addAttribute("symptomquestions", questionService.findAll());
		return "weight/add-weight";
	}

	@PostMapping("/weight/add")
	public String savePoll(@RequestParam(value = "action", required = true) String action,
			@Validated @ModelAttribute Sympweightbyday weight, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors().toString());
				model.addAttribute("symptomquestion", questionService.findAll());
				return "weight/add-weight";
			}
			weightService.save(weight, weight.getSymptomquestion().getSympquesId());
		}
		return "redirect:/weight/";
	}

	@GetMapping("/weight/edit/{id}")
	public String editWeight(@PathVariable("id") long id, Model model) {
		model.addAttribute("sympweightbyday", weightService.findById(id));
		model.addAttribute("symptomquestions", questionService.findAll());
		return "weight/edit-weight";
	}

	@PostMapping("/weight/edit/{id}")
	public String savePoll(@RequestParam(value = "action", required = true) String action, @PathVariable("id") long id,
			@Validated @ModelAttribute Sympweightbyday weight, BindingResult bindingResult, Model model) {
		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors().toString());
				model.addAttribute("symptomquestion", questionService.findAll());
				return "weight/edit-weight";
			}
			weight.setSympweidaysId(id);
			weightService.save(weight, weight.getSymptomquestion().getSympquesId());
		}
		return "redirect:/weight/";
	}

}
