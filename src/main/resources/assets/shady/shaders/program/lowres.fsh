#version 120

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;

varying vec2 texCoord;
varying float ratio;

void main()
{
	float aspectRatio = InSize.x/InSize.y;
    float lowresWidth = 256;
    float lowresHeight = aspectRatio * lowresWidth;

    float xScale = InSize.x / lowresWidth;
    float yScale = InSize.y / lowresHeight;
    float xPixel = InSize.x * texCoord.x;
    float yPixel = InSize.y * texCoord.y;
    xPixel = floor(xPixel / xScale) * xScale;
    yPixel = floor(yPixel / yScale) * yScale;
    vec2 lowresTexCoord = vec2(xPixel/InSize.x, yPixel/InSize.y);
	gl_FragColor = texture2D(DiffuseSampler, lowresTexCoord);
}
