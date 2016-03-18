#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;
uniform float scale;
uniform float zoom;
uniform float indexX;
uniform float indexY;

void main(void){
	
	//calculate correct UV coordinates with atlas indices
	float scalingX = 1.0/7.0;
	float scalingY = 1.0/4.0;
	
	pass_textureCoords = vec2(textureCoords.x*scalingX+scalingX*indexX, textureCoords.y*scalingY+scalingY*indexY);
	
	mat4 modelview = view * transform;
	
	//remove rotation/orientation for billboarding
	modelview[0][0] = scale*zoom;
	modelview[0][1] = 0;
	modelview[0][2] = 0;
	modelview[1][0] = 0;
	modelview[1][1] = scale*zoom;
	modelview[1][2] = 0;
	modelview[2][0] = 0;
	modelview[2][1] = 0;
	modelview[2][2] = scale*zoom;
	
	gl_Position = projection * modelview * vec4(position,1.0);
}