package com.codemyth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codemyth.entity.Student;
import com.codemyth.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/all")
	public String getAllStudent(Model model) {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findAll().forEach(studentList::add);
		model.addAttribute("studentList", studentList);
		return "students";
	}

	@GetMapping("/new")
	public String getStudentForm(Model model) {
		Student student = new Student();
		student.setResult(false);

		model.addAttribute("student", student);
		model.addAttribute("pageTitle", "Create New Student");
		return "student_form";
	}

	@PostMapping("/save")
	public String saveStudent(Student student, Model model, RedirectAttributes redirectAttributes) {
		studentRepository.save(student);
		redirectAttributes.addFlashAttribute("message", "Student Details has been saved");

		return "redirect:/students/all";

	}

	@GetMapping("/delete/{id}")
	public String deleteStudentById(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
		studentRepository.deleteById(id);
		return "redirect:/students/all";
	}

	@GetMapping("{id}")
	public String editStudentById(@PathVariable("id") long id, Model model) {
		Student s = studentRepository.findById(id).get();
		model.addAttribute("student", s);
		model.addAttribute("pageTitle", "Edit Student with Id " + id);
		return "student_form";
	}

	@GetMapping("{id}/result/{status}")
	public String updateResultById(@PathVariable("id") long id, @PathVariable("status") boolean status, Model mode,
			RedirectAttributes redirectAttributes) {

		studentRepository.updateResultStatus(id, status);
		String result = status ? "Pass" : "Fail";
		String message = "Student with Id " + id + " has been updated to " + result;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/students/all";
	}

	@GetMapping("/keyword")
	public String findStudentByNameContainsKeyword(@Param("keyword") String keyword, Model model,
			RedirectAttributes redirectAttributes) {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findByNameContainingIgnoreCase(keyword).forEach(studentList::add);
		model.addAttribute("studentList", studentList);
		String message = "Student who contains name as " + keyword;
		redirectAttributes.addFlashAttribute("message", message);
		return "students";
	}

}
