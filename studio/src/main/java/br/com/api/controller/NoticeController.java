package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.NoticeDTO;
import br.com.api.entity.repository.NoticeFilter;
import br.com.api.flow.notice.DeleteNoticeByIdentifierFlow;
import br.com.api.flow.notice.FindNoticeByFilterFlow;
import br.com.api.flow.notice.InsertNoticeFlow;
import br.com.api.flow.notice.UpdateNoticeFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/notice")
@CrossOrigin
public class NoticeController {

	@Autowired
	private InsertNoticeFlow insertNoticeFlow;
	@Autowired
	private UpdateNoticeFlow updateNoticeFlow;
	@Autowired
	private FindNoticeByFilterFlow findNoticeByFilterFlow;
	@Autowired
	private DeleteNoticeByIdentifierFlow deleteNoticeByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<NoticeDTO>> insert(@RequestBody NoticeDTO noticeDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertNoticeFlow.execute(noticeDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<NoticeDTO>> update(@RequestBody NoticeDTO noticeDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateNoticeFlow.execute(noticeDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<NoticeFilter>> find(@RequestBody NoticeFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findNoticeByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteNoticeByIdentifierFlow.execute(identifier, headers));
	}

}
