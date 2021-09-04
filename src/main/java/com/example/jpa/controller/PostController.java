package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Post;
import com.example.jpa.model.User;
import com.example.jpa.repository.PostRepository;
import com.example.jpa.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
	private UserRepository userRepository;
    
    //LOGGER
    private static Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
    	
    	 LOGGER.debug("in getAllPost a postcontroller");
    	 LOGGER.error("in getAllPost a postcontroller");
    	 LOGGER.info("in getAllPost a postcontroller");
      	 LOGGER.warn("in getAllPost a postcontroller");
    	 
        return postRepository.findAll(pageable);
    }

    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
    	System.out.println(post.getUser());
        return postRepository.save(post);
    }
    


    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

}
