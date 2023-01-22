package com.codemyth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
	public String getAll(Model model) {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findAll().forEach(studentList::add);
		model.addAttribute("studentList", studentList);
		return "students";
	}

	@GetMapping("/new")
	public String viewStudentForm(Model model) {
		Student student = new Student();
		student.setResult(false);

		model.addAttribute("student", student);
		model.addAttribute("pageTitle", "Create New Student");
		return "student_form";
	}

	@PostMapping("/save")
	public String saveStudent(Student student, RedirectAttributes redirectAttributes) {
		try {
			studentRepository.save(student);
			redirectAttributes.addFlashAttribute("message", "Student Details Saved Successfully");
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", e.getMessage());
		}

		return "redirect:/students/all";
	}

	@GetMapping("/{id}")
	public String editStudent(@PathVariable("id") long id, Model model) {
		Student student = studentRepository.findById(id).get();
		model.addAttribute("student", student);
		model.addAttribute("pageTitle", "Edit Student with id " + id);

		return "student_form";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
		studentRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "The Student with id " + id + "has been deleted Successfully");
		return "redirect:/students/all";
	}

	@GetMapping("/{id}/result/{status}")
	public String updateResultStatus(@PathVariable("id") long id, @PathVariable("status") boolean status,
			RedirectAttributes redirectAttributes) {
		try {
			studentRepository.updateResultStatus(id, status);
			String result = status ? "Pass" : "Fail";
			String message = "The Student with " + id + "updated to Result " + result;
			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/students/all";
	}

	@GetMapping("/keyword")
	public String getAllStudentContainKeyword(Model model, @Param("keyword") String keyword,
			RedirectAttributes redirectAttributes) {
		List<Student> studentList = new ArrayList<Student>();
		if (keyword == null || keyword.length() == 0) {
			redirectAttributes.addFlashAttribute("message", "No data Found with keyword " + keyword);
		} else {
			studentRepository.findByNameContainingIgnoreCase(keyword).forEach(studentList::add);
			redirectAttributes.addFlashAttribute("message", "Below Data Found with keyword " + keyword);
		}
		model.addAttribute("studentList", studentList);
		return "students";

	}
}
