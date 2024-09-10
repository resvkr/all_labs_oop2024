package com.ebabak.artserver.db;

import com.ebabak.artserver.entity.PixelRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixelRequestJPA extends JpaRepository<PixelRequest, Integer> {
}
