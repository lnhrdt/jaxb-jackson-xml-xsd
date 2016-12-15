package com.disney.awing.schedule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.smpte_ra.schemas._2021._2007.bxf.BxfMessage;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.util.ResourceUtils.getFile;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleUploadControllerTest {

    ScheduleUploadController subject;
    private MockMvc mockMvc;

    @Mock ScheduleService mockScheduleService;
    @Captor ArgumentCaptor<BxfMessage> bxfMessageArgumentCaptor;

    @Before
    public void setUp() {
        subject = new ScheduleUploadController(mockScheduleService);
        mockMvc = standaloneSetup(subject).build();
    }

    @Test
    public void upload_respondsToHttp() throws Exception {
        String sampleSchedule = loadFile("classpath:sample-schedules/KGO_D1161212_modifiedForTest.xml");

        mockMvc.perform(post("/schedules")
                .content(sampleSchedule)
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());
    }

    @Test
    public void upload_passesMarshaledSchedulesToService() throws Exception {
        String sampleSchedule = loadFile("classpath:sample-schedules/KGO_D1161212_modifiedForTest.xml");

        mockMvc.perform(post("/schedules")
                .content(sampleSchedule)
                .contentType(MediaType.APPLICATION_XML));

        verify(mockScheduleService).create(bxfMessageArgumentCaptor.capture());

        BxfMessage actualMessage = bxfMessageArgumentCaptor.getValue();
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage.getBxfData()).isNotNull();
    }

    private String loadFile(String path) throws IOException {
        return Files.lines(getFile(path).toPath()).collect(Collectors.joining());
    }
}
