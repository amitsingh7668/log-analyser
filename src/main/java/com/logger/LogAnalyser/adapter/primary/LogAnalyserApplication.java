package com.logger.LogAnalyser.adapter.primary;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.logger.LogAnalyser.corelogic.gateway.AlertEntity;
import com.logger.LogAnalyser.corelogic.gateway.AlertRepository;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.logger.LogAnalyser")
public class LogAnalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogAnalyserApplication.class, args);
	}
	
	@Bean
	public AlertRepository alertRepo()
	{
		return new AlertRepository() {
			
			@Override
			public <S extends AlertEntity> Optional<S> findOne(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> boolean exists(Example<S> example) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public <S extends AlertEntity> long count(Example<S> example) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public <S extends AlertEntity> S save(S entity) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Optional<AlertEntity> findById(String id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean existsById(String id) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void deleteById(String id) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAllById(Iterable<? extends String> ids) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAll(Iterable<? extends AlertEntity> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAll() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(AlertEntity entity) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public long count() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Page<AlertEntity> findAll(Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> S saveAndFlush(S entity) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> List<S> saveAll(Iterable<S> entities) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AlertEntity getReferenceById(String id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AlertEntity getOne(String id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AlertEntity getById(String id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void flush() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<AlertEntity> findAllById(Iterable<String> ids) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> List<S> findAll(Example<S> example, Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends AlertEntity> List<S> findAll(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<AlertEntity> findAll(Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<AlertEntity> findAll() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void deleteAllInBatch(Iterable<AlertEntity> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAllInBatch() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAllByIdInBatch(Iterable<String> ids) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
