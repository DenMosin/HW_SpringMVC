package ru.repository;

import ru.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepository {
    private Map<Long, Post> postMap = new ConcurrentHashMap<>();

    public PostRepository() {
        postMap.put(12L, new Post(12L, "someData"));
    }

    public Map<Long, Post> all() {
        return postMap;
    }

    public Optional<Post> getById(long id) {
        if (postMap.containsKey(id)) {
            return Optional.of(new Post(id, postMap.get(id).getData()));
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        final var newId = postMap.size() + 1L;
        if (post.getId() == 0) {
            postMap.put(newId, new Post(newId, post.getData()));
        } else {
            if (getById(post.getId()).isPresent()) {
                postMap.put(post.getId(), new Post(post.getId(), post.getData()));
            }
        }
        return post;
    }

    public void removeById(long id) {
        postMap.remove(id);
    }
}
