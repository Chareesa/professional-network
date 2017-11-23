package com.chareesa.exam3.coontrollers;

import com.chareesa.exam3.models.User;
import com.chareesa.exam3.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnotherController {
    private final UserService userService;

    public AnotherController(UserService userService) {
        super();
        this.userService = userService;
    }

//    @RequestMapping("/dashboard")
//    public String index(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/";
//        }
//        List<Course> all = courseService.findAll();
//        Map<Course, Boolean> courses = new HashMap<>();
//        for (Course course : all) {
//            courses.put(course, course.getStudentIds().contains(user.getId()));
//        }
//        model.addAttribute("courses", courses);
//        return "dashboard";
//    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        User dbUser = userService.findUser(user.getEmail());

        model.addAttribute("name", user.getName());
        model.addAttribute("description", user.getDescription());
        model.addAttribute("network", dbUser.getPeopleInNetwork());
        model.addAttribute("invitations", dbUser.getInvitations());
        return "home";
    }

    @RequestMapping("/users")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        List<User> all = usersNotConnected(user);
//        Map<User, Boolean> courses = new HashMap<>();
//        for (User course : all) {
//            courses.put(course, course.getUserIds().contains(user.getId()));
//        }
        model.addAttribute("users", all);
        return "users";
    }
    private List<User> usersNotConnected(User user) {
        List<User> all = userService.findAll();
        User me = userService.findUser(user.getEmail());
        List<User> myNetwork = me.getPeopleInNetwork();
        List<User> myInvitees = me.getInvitees();
        List<User> myInvitations = me.getInvitations();
        List<User> notInNetwork = new ArrayList();

        for (User person : all) {
            if (!myNetwork.contains(person) && !person.equals(me) && !person.getInvitations().contains(me) && !myInvitations.contains(person)) {
                notInNetwork.add(person);
            }
        }
        return notInNetwork;
    }
    @GetMapping("/user/profile/{id}")
    public String userProfile(HttpSession session, @PathVariable("id") long id, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        User dbUser = userService.findById(id);
        model.addAttribute("name", dbUser.getName());
        model.addAttribute("description", dbUser.getDescription());
        return "userProfile";
    }

    @GetMapping("/user/connect/{id}")
    public String sendInvite(HttpSession session, @PathVariable("id") long id, Model model) {
        // Get the user and me
        User otherPerson = userService.findById(id);
        User me = userService.findUser(((User) session.getAttribute("user")).getEmail());

        // Add me to their invitations list
        List<User> theirInvitations = otherPerson.getInvitations();
        theirInvitations.add(me);
        otherPerson.setInvitations(theirInvitations);

        // Add the other person to my invitees
//        List<User> myInvitees = me.getInvitees();
//        myInvitees.add(otherPerson);
//        me.setInvitees(myInvitees);

        // Save to database
        userService.addUser(me);
//        userService.addUser(otherPerson);
        return "redirect:/users";
    }
    @GetMapping("/user/accept/{id}")
    public String acceptInvite(HttpSession session, @PathVariable("id") long id, Model model) {
        // Get the user and me
        User otherPerson = userService.findById(id);
        User me = userService.findUser(((User) session.getAttribute("user")).getEmail());

        // Add me to their network
        List<User> theirNetwork = otherPerson.getPeopleInNetwork();
        theirNetwork.add(me);
        otherPerson.setPeopleInNetwork(theirNetwork);

        // Add the other person to my network
        List<User> myNetwork = me.getPeopleInNetwork();
        myNetwork.add(otherPerson);
        me.setPeopleInNetwork(myNetwork);

        //remove me from their invitees
//        List<User> theirInvitees = otherPerson.getInvitees();
//        theirInvitees.remove(me);
//        otherPerson.setInvitations(theirInvitees);

        //remove them from my invitations
        List<User> myInvitations = me.getInvitations();
        myInvitations.remove(otherPerson);
        me.setInvitations(myInvitations);

        // Save to database
        userService.addUser(me);
//        userService.addUser(otherPerson);
        return "redirect:/home";

        // Validation
//        if (me.getPeopleInNetwork().contains(otherPerson)) {
//            model.addAttribute("errorMsg", "You already have a course during that time!");
//            return "redirect:/dashboardError";
//        }
    }
    @GetMapping("/user/ignore/{id}")
    public String ignoreInvite(HttpSession session, @PathVariable("id") long id, Model model) {
        // Get the user and me
        User otherPerson = userService.findById(id);
        User me = userService.findUser(((User) session.getAttribute("user")).getEmail());

        // Remove me from their invitees list
//        List<User> theirInvitees = otherPerson.getInvitees();
//        theirInvitees.remove(me);
//        otherPerson.setInvitees(theirInvitees);

        // remove the other person from my invitations
        List<User> myInvitations = me.getInvitations();
        myInvitations.remove(otherPerson);
        me.setInvitations(myInvitations);

        // Save to database
        userService.addUser(me);
//        userService.addUser(otherPerson);
        return "redirect:/home";
    }
//
//    @RequestMapping("/create/course")
//    public String createCourse(HttpSession session, Model model, @ModelAttribute("course")Course course) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("days", DayOfWeek.values());
//        if (user == null) {
//            return "redirect:/";
//        }
//        return "createCourse";
//    }
//
//    @PostMapping("/add/course")
//    public String saveCourse(HttpSession session, @Valid @ModelAttribute("course") Course course) {
//        courseService.saveCourse(course);
//        return "redirect:/create/course";
//    }
//
//    @GetMapping("/user/addCourse/{id}")
//    public String saveStudentCourse(HttpSession session, @PathVariable("id") long id, Model model) {
//        // Get the course and the user
//        Course thisCourse = courseService.findById(id);
//        User user = userService.findUser(((User) session.getAttribute("user")).getEmail());
//
//        // Validation
//        if (courseOverlaps(thisCourse, user)) {
//            model.addAttribute("errorMsg", "You already have a course during that time!");
//            return "redirect:/dashboardError";
//        }
//
//        // Add the user to the course
//        List<User> currentStudents = thisCourse.getStudents();
//        currentStudents.add((User) session.getAttribute("user"));
//        thisCourse.setStudents(currentStudents);
//
//        // Add the course to the user
//        List<Course> courses = user.getCourses();
//        courses.add(thisCourse);
//        user.setCourses(courses);
//
//        // Save to database
//        userService.addUser(user);
//        courseService.addStudent(thisCourse);
//        return "redirect:/dashboard";
//    }
//
//    private boolean courseOverlaps(Course addedCourse, User user) {
//        List<Course> usersCourses = user.getCourses();
//        for (Course existingCourse : usersCourses) {
//            if (overLaps(addedCourse, existingCourse)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean overLaps(Course addedCourse, Course existingCourse) {
//        List<DayOfWeek> addDays = addedCourse.getDays();
//
//        // Check if on the same day
//        boolean sameDay = false;
//        for (DayOfWeek addDay : addDays) {
//            if (existingCourse.getDays().contains(addDay)) {
//                sameDay = true;
//                break;
//            }
//        }
//
//        // Check if at the same time
//        if (sameDay) {
//            LocalTime addedStartTime = addedCourse.getStartTime();
//            LocalTime addedEndTime = addedCourse.getEndTime();
//            LocalTime existingStartTime = existingCourse.getStartTime();
//            LocalTime existingEndTime = existingCourse.getEndTime();
//
//            return (addedStartTime.equals(existingStartTime)
//                    || (addedStartTime.isAfter(existingStartTime) && addedStartTime.isBefore(existingEndTime)))
//                    || addedEndTime.equals(existingEndTime)
//                    || (addedEndTime.isBefore(existingEndTime) && addedEndTime.isAfter(existingStartTime));
//        }
//
//        return false;
//    }
}
