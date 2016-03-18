#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transform;
uniform mat4 projection;
uniform float width;
uniform float height;
uniform float texWidth;
uniform float texHeight;
uniform float indexX;
uniform float indexY;

void main(void){
	
	//calculate correct UV coordinates with atlas indices
	float scalingX = 1.0/texWidth;
	float scalingY = 1.0/texHeight;
	
	pass_textureCoords = vec2(textureCoords.x*scalingX*width+scalingX*indexX, textureCoords.y*scalingY*height+scalingY*indexY);
	
	gl_Position = projection * transform * vec4(position,1.0);
}