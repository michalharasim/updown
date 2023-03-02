package com.updown.repository;


import com.updown.model.FileInfo;
import com.updown.model.FileInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, String> {

}
