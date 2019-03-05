package net.jeemi.maven.plugin.mar;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

/*
 * Based on
 * https://github.com/apache/maven-war-plugin/blob/master/
 * src/main/java/org/apache/maven/plugins/war/AbstractWarMojo.java
 * 
 * Taking into account
 * https://github.com/sonatype/plexus-archiver/blob/master/
 * src/main/java/org/codehaus/plexus/archiver/war/WarArchiver.java
 */
@Mojo(
	name="mar",
	defaultPhase=LifecyclePhase.PACKAGE,
	threadSafe=true,
	requiresDependencyResolution=ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class MarMojo
extends AbstractMojo
{
	private static final String EXTENSION = "mar";
	private static final String MACHINE_XML_FILENAME = "machine.xml";
	
	private static final int BUFFER_SIZE = 4 * 1024;

	@Component
	private MavenProjectHelper mavenProjectHelper;
	
	@Parameter(
		required=true,
		readonly=true,
		defaultValue="${project}"
	)
	private MavenProject project;
	
	@Parameter(
		required=true,
		defaultValue="${project.build.directory}"
	)
	private File buildDirectory;
	
	@Parameter(
		required=true,
		defaultValue="${project.build.outputDirectory}"
	)
	private File buildOutputDirectory;
	
	@Parameter(
		readonly=true,
		defaultValue="${project.build.finalName}"
	)
	private String finalName;
	
	@Parameter
	private String classifier;
	
	@Parameter(
		defaultValue="${basedir}/src/main/machinery"
	)
	private File machineryDirectory;
	
	@Parameter(
		property="maven.mar.skip",
		defaultValue="false"
	)
    private boolean skip;
	
	@Parameter
	private MavenArchiveConfiguration archive;
	
	public MarMojo()
	{
		mavenProjectHelper = null;
		project = null;
		buildDirectory = null;
		buildOutputDirectory = null;
		finalName = null;
		classifier = null;
		machineryDirectory = null;
		skip = false;
		archive = new MavenArchiveConfiguration();
	}
	
	@Override
	public void execute()
	throws MojoExecutionException, MojoFailureException
	{
		if (skip)
			getLog().info("Skipping the execution.");
		else
			executeWithoutSkipping();
	}
	
	public void executeWithoutSkipping()
	throws MojoExecutionException, MojoFailureException
	{
		File targetFile = getTargetFile();
		build(targetFile);
		project.getArtifact().setFile(targetFile);
	}
	
	private File getTargetFile()
	{
		return new File(buildDirectory, new StringBuilder()
			.append(requireNonNull(finalName))
			.append(Optional.ofNullable(classifier)
				.map(value -> format("-%s", value))
				.orElse(""))
			.append(".")
			.append(EXTENSION)
			.toString());
	}
	
	private void build(File targetFile)
	throws MojoExecutionException
	{
		try (ZipOutputStream output = new ZipOutputStream(
				new FileOutputStream(targetFile)))
		{
			File machineXmlFile = new File(
				new File(machineryDirectory, "MACHINE-INF"),
				MACHINE_XML_FILENAME);
			output.putNextEntry(new ZipEntry("MACHINE-INF/machine.xml"));
			copy(machineXmlFile, output);
			output.closeEntry();
		}
		catch (IOException exception)
		{
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}
	
	private static void copy(File sourceFile, OutputStream output)
	throws IOException
	{
		try (InputStream input = new FileInputStream(sourceFile))
		{
			byte[] buf = new byte[BUFFER_SIZE];
			int len = input.read(buf, 0, BUFFER_SIZE);
			while (len > 0)
			{
				output.write(buf, 0, len);
				len = input.read(buf, 0, BUFFER_SIZE);
			}
		}
	}
}
