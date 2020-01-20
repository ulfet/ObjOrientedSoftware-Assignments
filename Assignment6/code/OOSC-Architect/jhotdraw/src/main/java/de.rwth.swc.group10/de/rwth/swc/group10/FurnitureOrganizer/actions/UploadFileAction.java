package de.rwth.swc.group10.FurnitureOrganizer.actions;

import org.jhotdraw.annotation.Nullable;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.AbstractViewAction;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.io.ImageOutputFormat;
import org.jhotdraw.samples.draw.DrawView;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class UploadFileAction extends AbstractViewAction {

    final ImageOutputFormat outputFormat;

	public UploadFileAction(Application a, @Nullable View pr) {
		super(a, pr);

		outputFormat = new ImageOutputFormat();
	}
	
    @Override
    public void actionPerformed(ActionEvent evt) {

        DrawView view = (DrawView) this.getActiveView();
        Drawing drawing = view.getEditor().getActiveView().getDrawing();

        try {

            URL url = new URL("http://localhost:8080/images/file");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput( true );
            connection.setUseCaches( false );

            // Does not work correctly
            outputFormat.write(connection.getOutputStream(), drawing);

            // TODO: Just for debugging
            File file = new File("./temp.png");
            outputFormat.write(file, drawing);

            // TODO: Just for debugging
            Map<String, List<String>> map = connection.getHeaderFields();
            System.out.println("Printing Response Header...\n");
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey()
                        + " ,Value : " + entry.getValue());
            }

            // Get the location
            String location = connection.getHeaderField("Location");

            if (location == null) {
                System.out.println("Key 'Location' is not found!");
            } else {
                System.out.println("Location - " + location);
            }

            // TODO: Create image class and upload
            String json = "{\n" + 
            		"  \"id\": \"42\",\n" + 
            		"  \"name\": \"Philipp\",\n" + 
            		"  \"location\": \""+ location + "\",\n" + 
            		"  \"publishedAt\": \"2020-01-20\",\n" + 
            		"  \"lastModified\": \"2020-01-20\"\n" + 
            		"}";
            
            url = new URL("http://localhost:8080/images");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(json);
            writer.flush();

            int code = connection.getResponseCode();

            if (code == 201) {
                System.out.println("Create request successful!");
            }
            else {
                System.out.println("Create requests ends with status " + code);
            }

            writer.close();

        } catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
            ex.printStackTrace();
        } catch (IOException ex) {
			// TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }
	
}
