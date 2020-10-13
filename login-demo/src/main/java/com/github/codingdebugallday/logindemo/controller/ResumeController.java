package com.github.codingdebugallday.logindemo.controller;

import java.util.List;

import com.github.codingdebugallday.logindemo.pojo.Resume;
import com.github.codingdebugallday.logindemo.service.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/22 10:21
 * @since 1.0.0
 */
@Controller
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Resume> list = resumeService.list();
        model.addAttribute("resumeList", list);
        return "/resume-list";
    }

    @RequestMapping("/add")
    public String add() {
        return "resume-add";
    }

    @RequestMapping("/insert")
    public ModelAndView insert(ModelAndView modelAndView, String name, String address, String phone) {
        Resume resume = new Resume(name, address, phone);
        resumeService.insert(resume);
        modelAndView.setViewName("redirect:/resume/list");
        return modelAndView;
    }

    @RequestMapping("/pre-update")
    public ModelAndView preUpdate(ModelAndView modelAndView, Long id) {
        Resume resume = resumeService.getById(id);
        modelAndView.addObject(resume);
        modelAndView.setViewName("resume-update");
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(ModelAndView modelAndView, Long id, String name, String address, String phone) {
        Resume resume = new Resume(name, address, phone);
        resume.setId(id);
        resumeService.update(resume);
        modelAndView.setViewName("redirect:/resume/list");
        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(ModelAndView modelAndView, @RequestParam(value = "id") Long id) {
        resumeService.delete(id);
        modelAndView.setViewName("redirect:/resume/list");
        return modelAndView;
    }

}
