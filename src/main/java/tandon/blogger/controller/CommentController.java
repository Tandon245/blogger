package tandon.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tandon.blogger.model.Comment;
import tandon.blogger.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

}
