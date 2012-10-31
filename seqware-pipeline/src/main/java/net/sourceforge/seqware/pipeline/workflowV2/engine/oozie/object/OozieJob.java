package net.sourceforge.seqware.pipeline.workflowV2.engine.oozie.object;

import net.sourceforge.seqware.pipeline.workflowV2.model.AbstractJob;

import org.jdom.Element;

public class OozieJob {
	private String okTo;
	private String errorTo;
	private String name;
	private AbstractJob jobObj;
	
	
	public OozieJob(AbstractJob job, String name) {
		this.name = name;
		this.jobObj = job;
	}
	
	public Element serializeXML() {
		Element element = new Element("action", WorkflowApp.NAMESPACE);
		element.setAttribute("name", this.name);
		Element javaE = this.getJavaElement();
		element.addContent(javaE);
		
		//okTo
		Element ok = new Element("ok", WorkflowApp.NAMESPACE);
		ok.setAttribute("to","end");
		element.addContent(ok);
		
		Element error = new Element("error", WorkflowApp.NAMESPACE);
		error.setAttribute("to","fail");
		element.addContent(error);

		return element;
	}
	
	public String getName() {
		return this.name;
	}
	
	private Element getJavaElement() {
		Element javaE = new Element("java", WorkflowApp.NAMESPACE);
		Element jobTracker = new Element("job-tracker", WorkflowApp.NAMESPACE);
		jobTracker.setText("${jobTracker}");
		javaE.addContent(jobTracker);
		
		Element nameNode = new Element("name-node", WorkflowApp.NAMESPACE);
		nameNode.setText("${nameNode}");
		javaE.addContent(nameNode);
		
		Element config = new Element("configuration", WorkflowApp.NAMESPACE);
		Element p0 = new Element("property", WorkflowApp.NAMESPACE);
		config.addContent(p0);
		Element name0 = new Element("name",WorkflowApp.NAMESPACE);
		name0.setText("mapred.job.queue.name");
		p0.addContent(name0);
		Element value0 = new Element("value",WorkflowApp.NAMESPACE);
		value0.setText("${queueName}");
		p0.addContent(value0);
		javaE.addContent(config);
		
		Element mainClass = new Element("main-class", WorkflowApp.NAMESPACE);
		mainClass.setText("net.sourceforge.seqware.pipeline.runner.Runner");
		javaE.addContent(mainClass);
		
		Element arg0 = new Element("arg",WorkflowApp.NAMESPACE);
		arg0.setText("--no-metadata");
		javaE.addContent(arg0);
		
		Element arg1 = new Element("arg", WorkflowApp.NAMESPACE);
		arg1.setText("--module");
		javaE.addContent(arg1);
		
		Element argModule = new Element("arg", WorkflowApp.NAMESPACE);
		argModule.setText("net.sourceforge.seqware.pipeline.modules.GenericCommandRunner");
		javaE.addContent(argModule);
		
		Element dash = new Element("arg", WorkflowApp.NAMESPACE);
		dash.setText("--");
		javaE.addContent(dash);
		
		Element command = new Element("arg", WorkflowApp.NAMESPACE);
		command.setText("--gcr-command");
		javaE.addContent(command);
		
		for(String arg: this.jobObj.getCommand().getArguments()) {
			Element cmdArg = new Element("arg", WorkflowApp.NAMESPACE);
			cmdArg.setText(arg);
			javaE.addContent(cmdArg);
		}
			
		return javaE;
	}
}
