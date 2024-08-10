package com.its.issuetrackingservice.infrastructure.persistence.repository;

import com.its.issuetrackingservice.infrastructure.persistence.entity.ActivityItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityItemRepository extends JpaRepository<ActivityItem, Long> {

}
