package de.rwth.swc.group10.FurnitureOrganizer;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.samples.draw.DrawView;

import java.io.IOException;
import java.net.URI;

public class FurnitureOrganizerView extends DrawView {
    @Override
    public void write(URI f, URIChooser fc) throws IOException {
        // Get the actual drawing
        Drawing drawing = getEditor().getActiveView().getDrawing();

        // Set the output format to the default value
        OutputFormat outputFormat = drawing.getOutputFormats().get(0);

        // Try to find a format, which matches the uri
        for (OutputFormat format : drawing.getOutputFormats()) {
            if (f.toString().endsWith(format.getFileExtension())) {
                outputFormat = format;
            }
        }

        // Outputs the drawing in the selected format
        outputFormat.write(f, drawing);
    }
}
